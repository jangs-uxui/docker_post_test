import axios from "axios";
import {setToken, store} from "../store/store";

const apiClient = axios.create({
    baseURL: "/api",
    headers: {"Content-Type": "application/json"},
    withCredentials: true, // 쿠키,인증허용
    timeout: 5000
});

// 요청 서버에 보내기 직전 실행
apiClient.interceptors.request.use(config => {
        if (config.data && config.data instanceof URLSearchParams) {
            config.headers["Content-Type"] = "application/x-www-form-urlencoded";
        }
        const jwt = store.getState().auth.token; // store에서 토큰 꺼내서
        config.headers["Authorization"] = jwt; // 요청헤더에 추가
        return config; // 수정된 config 반환
    },
    error => Promise.reject(error)
);

// 요청에 대한 response 들어오기 직전
apiClient.interceptors.response.use(
    response => response, // 오류없으면 그냥 통과
    async (error) => { // 오류시(토큰만료)
        const originalRequest = error.config; // 실패한 요청객체
        if (error.response && error.response.status === 456 && !originalRequest._retry) {
            originalRequest._retry = true; // 무한반복 방지
            try {
                const response = await axios.post("/api/reissue", null, {withCredentials: true});
                // 요청성공시
                const access = response.headers["authorization"]; // 헤더에서 토큰 가져옴
                store.dispatch(setToken(access)); // redux저장 (store의 dispatch 사용*)
                console.log("만료된 요청 재시도");
                return apiClient(originalRequest); // 실패했던 요청객체 넘기며 재요청**
            } catch (error) {
                console.log("refreshToken 재발행 실패", error);
                return Promise.reject(error); // 오류 넘김
            }
        }
        return Promise.reject(error);
    }
);

export default apiClient;