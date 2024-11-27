import {createBrowserRouter, Navigate} from "react-router-dom";
import {MainLayout} from "./layouts/MainLayout";
import {Orders} from "./components/Orders/Orders";
import Login from "./components/Login/Login";



const router = createBrowserRouter([
    {
        path: '', element: <MainLayout />, children: [
            {index: true, element: <Navigate to={'auth'}/>},
            {path: 'auth', element:<Login/>},
            { path: 'orders', element: <Orders /> }

        ]


    }])

export {router}