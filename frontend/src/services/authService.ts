import axios from "axios";
import {urls} from "../constants/urls";
import {axiosService} from "./axios.services";

const authService = {
        signUpAdmin: () => axiosService.post(urls.auth.signUpAdmin),
        signUp: (data: { name: string; email: string; password: string }) => axiosService.post(urls.auth.signUp, data),
        signIn: (credentials: { email: string; password: string }) => axiosService.post(urls.auth.signIn, credentials),
        refresh: (refreshToken: string) => axios.post(urls.auth.refresh, { refreshToken }),

}

export {authService}