import {IUser} from "../../interfaces/authInterface";
import {authService} from "../../services/authService";
import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {AxiosError} from "axios";

interface AuthState {
    user: IUser | null;
    accessToken: string | null;
    refreshToken: string | null;
    error: string | null;
    loading: boolean;
}

const initialState: AuthState = {
    user: null,
    accessToken: null,
    refreshToken: null,
    error: null,
    loading: false,
};


export const signUpAdmin = createAsyncThunk(
    "auth/signUpAdmin",
    async (_, { rejectWithValue }) => {
        try {
            const response = await authService.signUpAdmin();
            return response.data;
        } catch (e) {
            const err = e as AxiosError;
            return rejectWithValue(err.response?.data);
        }
    }
);
/*ЛОГІН*/
export const signIn = createAsyncThunk(
    "auth/signIn",
    async (credentials: { email: string; password: string }, { rejectWithValue }) => {
        try {
            const response = await authService.signIn(credentials);
            return response.data; // Це має повертати ім'я користувача
        } catch (e) {
            const err = e as AxiosError;
            return rejectWithValue(err.response?.data);
        }
    }
);

const authSlice = createSlice({
    name: "authSlice",
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builder
            .addCase(signUpAdmin.fulfilled, (state, action) => {
                state.user = action.payload;
            })
            .addCase(signIn.fulfilled, (state, action) => {
                console.log(action.payload);
                state.user = action.payload.data.user;
                state.accessToken = action.payload.data.accessToken;
                state.refreshToken = action.payload.data.refreshToken;
            })

    }
})

const{reducer:authReducer,actions}=authSlice


const authAction={
    ...actions,
    signUpAdmin,
    signIn
}


export {
    authAction,
    authReducer
}