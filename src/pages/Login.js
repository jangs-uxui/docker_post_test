import apiClient from "../api/axiosInstance";
import {useDispatch} from "react-redux";
import {setIsLoggedIn, setRole, setToken, setUsername} from "../store/store";
import {useNavigate} from "react-router-dom";

export default function Login() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await apiClient.post("/login",
                new URLSearchParams({
                    username: e.target.username.value,
                    password: e.target.password.value
                })
            );
            // 로그인성공시
            console.log(response.data);
            dispatch(setToken(response.headers["authorization"])); // redux에 저장 (access토큰)
            dispatch(setUsername(response.data.username));
            dispatch(setRole(response.data.role)); // role 저장
            dispatch(setIsLoggedIn(true)); // 로그인
            alert(response.data.result);
            navigate("/");
        } catch (error) {
            if (error.response && error.response.status === 401) {
                console.log(error.response.data);
            } else {
                console.log(error)
            }
        }
    }

    return (
        <>
            <h2>로그인</h2>
            <form onSubmit={handleLogin}>
                <label>ID: <input type="text" name="username"/></label><br/>
                <label>PW: <input type="password" name="password"/></label><br/>
                <button type="submit">로그인</button>
            </form>
        </>
    );
}