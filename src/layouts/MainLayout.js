import {Outlet} from "react-router-dom";
import Header from "./Header";
import Nav from "./Nav";
import {useEffect} from "react";
import apiClient from "../api/axiosInstance";
import {setPostList} from "../store/store";
import {useDispatch} from "react-redux";

export default function MainLayout() {
    const dispatch = useDispatch();

    // 마운트 될 때 post리스트 요청
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await apiClient.get("/posts");
                console.log(response.data);
                dispatch(setPostList(response.data));
            } catch (error) {
                console.log("리스트 요청 실패", error);
            }
        }
        fetchData();
    }, [])

    return (
        <>
            <Header title="게시판" />
            <hr/>
            <Nav />
            <hr/>
            <Outlet/>
        </>
    );
}