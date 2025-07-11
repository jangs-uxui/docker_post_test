import {useSelector} from "react-redux";
import {Link} from "react-router-dom";

export default function PostList() {
    const postList = useSelector(state => state.post.postList);

    return (
        <>
            <h2>게시글</h2>
            <ul className="post-list">
                {postList.map(post =>
                    <li key={post.id}><Link to={"/post/"+post.id}>{post.title}</Link></li>
                )}
            </ul>
        </>
    );
}