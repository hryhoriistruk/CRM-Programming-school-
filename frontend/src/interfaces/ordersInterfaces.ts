export interface IOrderResponse {
    data: IOrder[];
    itemsFound: number;
    limit: number;
    page: number;
}

export interface IOrder {
    id: number;
    name: string;
    surname: string;
    email: string;
    phone: string;
    age: number;
    course: string;
    course_format: string;
    course_type: string;
    sum: number | null;
    already_paid: boolean | null;
    created_at: string;
    utm: string;
    msg: string | null;
    status: string | null;
}
