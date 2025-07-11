import {Link} from "react-router-dom";
import {useSelector} from "react-redux";

export default function Nav() {
    const isLoggedIn = useSelector(state => state.auth.isLoggedIn);


    const menuList = [
        {id: 1, title: "Home", path: "/"},
        {id: 2, title: "게시글", path: "/post/list"}
    ];
    if (isLoggedIn) { // 로그인시
        menuList.push({id: 3, title: "글쓰기", path: "/post/new"});
        menuList.push({id: 4, title: "로그아웃", path: "/logout"});
    } else { // 로그아웃 시
        menuList.push({id: 3, title: "로그인", path: "/login"});
        menuList.push({id: 4, title: "회원가입", path: "/join"});
    }
    
    return (
        <nav>
            <ul>
                {menuList.map(item =>
                    <li key={item.id}><Link to={item.path}>{item.title}</Link></li>
                )}
            </ul>
        </nav>
    );
}