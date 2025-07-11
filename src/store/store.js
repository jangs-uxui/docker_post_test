import {combineReducers, configureStore, createSlice} from "@reduxjs/toolkit";
import {persistReducer, persistStore} from "redux-persist";
import storage from "redux-persist/lib/storage"; // 수동추가

const authSlice = createSlice({
    name: "auth",
    initialState: {
        token: null,
        username: null,
        role: null,
        isLoggedIn: false,
    },
    reducers: {
        setToken: (state, action) => {
            state.token = action.payload;
        },
        setUsername: (state, action) => {
            state.username = action.payload;
        },
        setRole: (state, action) => {
            state.role = action.payload;
        },
        setIsLoggedIn: (state, action) => {
            state.isLoggedIn = action.payload;
        }
    }
});

const postSlice = createSlice({
    name: "post",
    initialState: {
        postList: [],
        count: 0
    },
    reducers: {
        setPostList: (state, action) => {
            state.postList = action.payload;
            state.count = action.payload.length;
        },
        addPost: (state, action) => {
            state.postList.push(action.payload);
            state.count = state.postList.length;
        },
        updatePost: (state, action) => {
            const updatedPost = action.payload;
            const index = state.postList.findIndex(post => post.id === action.payload.id); // 못찾으면 -1
            if (index !== -1) {
                state.postList[index] = updatedPost;
            }
        },
        deletePost: (state, action) => {
            state.postList = state.postList.filter((item) => item.id !== action.payload);
            state.count = state.postList.length;
        }
    }
});

// Local Storage 설정
const persistConfig = {
    key: 'root',
    storage, // (수동import)
    whitelist: ['auth', 'post'],
};
// reducer 묶음
const rootReducer = combineReducers({
    auth: authSlice.reducer,
    post: postSlice.reducer,
});
// reducer 확장
const persistedReducer = persistReducer(persistConfig, rootReducer);

// store + persistor export
export const store = configureStore({
    reducer: persistedReducer, // 통합된 reducer 대입
});
// persistor 객체 : 동기화,복원 작업 수행
export const persistor = persistStore(store);

// action export
export const {setToken,setUsername, setIsLoggedIn, setRole} = authSlice.actions;
export const {setPostList, addPost, updatePost, deletePost} = postSlice.actions;
