import {useDispatch, useSelector} from "react-redux";
import apiClient from "../api/axiosInstance";
import {useNavigate} from "react-router-dom";
import {addPost} from "../store/store";

export default function PostNew() {
    const user = useSelector((state) => state.auth);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await apiClient.post("/post", {
                title: e.target.title.value,
                body: e.target.body.value,
                username: user.username,
            });
            console.log(response.data);
            dispatch(addPost(response.data)); // redux에 추가
            alert("글쓰기 성공");
            navigate("/post/list");
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <>
            <h2>글쓰기</h2>
            <form onSubmit={handleSubmit}>
                <label>제목: <input type="text" name="title" /></label><br/>
                <label>내용: <textarea name="body" placeholder="내용을 입력해 주세요." cols="50" rows="10"/></label><br/>
                <p>작성자: {user.username}</p>
                <button type="submit">글쓰기</button>
            </form>
        </>
    );
}