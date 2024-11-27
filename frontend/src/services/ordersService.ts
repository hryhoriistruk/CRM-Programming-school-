import {IOrder, IOrderResponse} from "../interfaces/ordersInterfaces";
import {axiosService} from "./axios.services";
import {urls} from "../constants/urls";
import {IRes} from "../types/resType";

const orderService = {
    getAll: (query: Record<string, string>): IRes<IOrderResponse> => {

        return axiosService.get(urls.orders.base, {
            params: query,
        });
    }
};


export {orderService}