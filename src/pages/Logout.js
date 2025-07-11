import {useNavigate} from "react-router-dom";
import apiClient from "../api/axiosInstance";
import {useEffect} from "react";
import {useDispatch} from "react-redux";
import {setIsLoggedIn, setRole, setToken, setUsername} from "../store/store";


export default function Logout() {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
       const fetchData = async () => {
           try {
               // 만료쿠키로 대체
               const response = await apiClient.delete("/reissue");
               // dispatch(setToken(response.data.token));
               dispatch(setToken(null)); // access 토큰삭제
               dispatch(setUsername(null));
               dispatch(setIsLoggedIn(false)); // 로그아웃
               dispatch(setRole(null)); // role 삭제
               console.log(response.data);
               navigate("/");
           } catch (error) {
               console.log("로그아웃 실패", error);
           }
       }
       fetchData();
    }, [])

    return (
        <>
            <p>로그아웃 중입니다...</p>
        </>
    );
}