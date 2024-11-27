import {useAppDispatch, useAppSelector} from "../../hooks/reduxHooks";
import {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
// @ts-ignore
import style from "./Login.module.css"

import {authAction} from "../../redux/slices/authSlice";


const Login: React.FC = () => {
    const dispatch = useAppDispatch()
    const navigate = useNavigate();
    const { user, loading, error } = useAppSelector((state) => state.auth);
    console.log("User:", user);
    console.log("Loading:", loading);
    console.log("Error:", error);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleLogin = () => {
        dispatch(authAction.signIn({ email, password }));

    };
    useEffect(() => {
        if (user) {
            navigate("/orders");
        }
    }, [user, navigate]);

    return (
        <div className={style.main}>
            <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email"
            />
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="Password"
            />
            <button onClick={handleLogin} disabled={loading}>Login</button>


        </div>
    );
};

export default Login