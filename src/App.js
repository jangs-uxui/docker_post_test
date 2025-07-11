import './App.css';

import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import Intro from "./pages/Intro";
import PostList from "./pages/PostList";
import Login from "./pages/Login";
import Join from "./pages/Join";
import Logout from "./pages/Logout";
import PostDetail from "./pages/PostDetail";
import PostNew from "./pages/PostNew";
import PostUpdate from "./pages/PostUpdate";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MainLayout/>}>
                    <Route index element={<Intro/>} />
                    <Route path="/post/list" element={<PostList/>} />
                    <Route path="/post/:id" element={<PostDetail/>}/>
                    <Route path="/post/new" element={<PostNew/>}/>
                    <Route path="/post/update/:id" element={<PostUpdate/>}/>
                    <Route path="/login" element={<Login/>} />
                    <Route path="/logout" element={<Logout/>} />
                    <Route path="/join" element={<Join/>} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
