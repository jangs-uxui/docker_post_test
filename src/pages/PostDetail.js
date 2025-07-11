import {Link, useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import apiClient from "../api/axiosInstance";
import {deletePost} from "../store/store";

export default function PostDetail() {
    const {id} = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const postList = useSelector((state) => state.post.postList);
    const username = useSelector((state) => state.auth.username);
    const post = postList.find(post => post.id === Number(id));

    const handleDelete = async (e) => {
        const response = await apiClient.delete("/post/" + post.id);
        console.log(response.data);
        dispatch(deletePost(post.id));
        alert(response.data);
        navigate("/post/list");
    }

    // post가 존재할 때만 렌더링 (삭제후 재렌더링 일어날때 오류방지)
    if (!post) return <p>게시글을 찾을 수 없습니다.</p>;

    return (
        <>
            <h2>글내용</h2>
            <h3>{post.title}</h3>
            <div>{post.body}</div>
            <p>작성자: {post.username}</p>
            <Link to="/post/list" className="btn">목록으로</Link>
            {username === post.username &&
                <>
                    <Link to={"/post/update/" + post.id} className="btn">수정하기</Link>
                    <button onClick={handleDelete}>삭제하기</button>
                </>
            }
        </>
    );
}