import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {IOrder, IOrderResponse} from "../../interfaces/ordersInterfaces";
import {orderService} from "../../services/ordersService";
import {AxiosError} from "axios";

interface IState {
    order: IOrder[];
    trigger: boolean;
    limit: number;
    page: number;
    itemsFound: number;
}

const initialState: IState = {
    order: [],
    limit: 25,
    page: 1,
    trigger: true,
    itemsFound: 0,
};


const getAll = createAsyncThunk<IOrderResponse, { query: Record<string, string> }>(
    'orderSlice/getAll',
    async ({ query }, { rejectWithValue }) => {
        try {
            const response = await orderService.getAll(query);
            return response.data;
        } catch (e) {
            const err = e as AxiosError;
            return rejectWithValue(err.response?.data);
        }
    }
);
const orderSlice = createSlice({
    name: 'orderSlice',
    initialState,
    reducers: {},
    extraReducers: builder =>
        builder
            .addCase(getAll.fulfilled, (state, action) => {
                state.order = action.payload.data;
                state.itemsFound = action.payload.itemsFound;
                state.limit = action.payload.limit;
                state.page = action.payload.page; 
            })});
const{reducer:orderReducer, actions}=orderSlice


const orderAction={
    ...actions,
    getAll
}


export {
    orderAction,
orderReducer}