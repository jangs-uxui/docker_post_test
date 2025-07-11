import {useDispatch, useSelector} from "react-redux";
import {useNavigate, useParams} from "react-router-dom";
import apiClient from "../api/axiosInstance";
import {updatePost} from "../store/store";
import {useEffect, useState} from "react";

export default function PostUpdate() {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const user = useSelector(state => state.auth);
    const {id} = useParams();
    const postList = useSelector((state) => state.post.postList);
    const post = postList.find(post => post.id === Number(id));

    // 로컬 상태 추가
    const [title, setTitle] = useState("");
    const [body, setBody] = useState("");
    // post 데이터를 가져와 초기화
    useEffect(() => {
        if (post) {
            setTitle(post.title);
            setBody(post.body);
        }
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await apiClient.put("/post", {
                id: id,
                title: title,
                body: body,
                username: user.username,
            });
            console.log(response.data);
            dispatch(updatePost(response.data)); // redux수정
            alert("수정 성공");
            navigate("/post/"+id);
        } catch (error) {
            console.error(error);
        }
    }


    return (
        <>
            <h2>수정하기</h2>
            <form onSubmit={handleSubmit}>
                <label>제목: <input type="text" name="title" value={title} onChange={e => setTitle(e.target.value)} /></label><br/>
                <label>내용: <textarea name="body" placeholder="내용을 입력해 주세요." cols="50" rows="10" value={body} onChange={e => setBody(e.target.value)}/></label><br/>
                <p>작성자: {user.username}</p>
                <button type="submit">수정하기</button>
            </form>
        </>
    );
}