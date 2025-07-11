import apiClient from "../api/axiosInstance";
import {useNavigate} from "react-router-dom";

export default function Join() {
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await apiClient.post("/user", {
                username: e.target.username.value,
                password: e.target.password.value,
                fullname: e.target.fullname.value,
            });
            console.log(response.data);
            alert(response.data);
            navigate("/login")
        } catch (error) {
            if (error.response.status === 409) { // id중복
                console.error(error.response);
                alert(error.response.data);
            } else if (error.response.status === 422) { // 유효성검증 오류
                console.error(error.response);
                alert(error.response.data);
            } else {
                console.error(error);
                alert("로그인 실패");
            }
        }

    }

    return (
        <>
            <h2>회원가입</h2>
            <form onSubmit={handleSubmit}>
                <label>ID: <input type="text" name="username" /></label><br/>
                <label>PW: <input type="password" name="password" /></label><br/>
                <label>이름: <input type="text" name="fullname" /></label><br/>
                <button type="submit">가입하기</button>
            </form>
        </>
    );
}