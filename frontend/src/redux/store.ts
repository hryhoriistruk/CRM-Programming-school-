import {configureStore} from "@reduxjs/toolkit";
import {orderReducer} from "./slices/ordersSlice";
import {authReducer} from "./slices/authSlice";



const store = configureStore({
    reducer: {
        order:orderReducer,
        auth: authReducer
     }
});

export {
    store
}