import React, {FC} from 'react';
import {IOrder} from "../../interfaces/ordersInterfaces";


interface IProps {
    order: IOrder

}
const Order:FC<IProps> = ({order}) => {
const{id,name,email,phone,age,course,course_format,course_type,surname,status,already_paid,sum,created_at}=order
    return (
        <tr >
            <td>{id}</td>
            <td>{name}</td>
            <td>{surname}</td>
            <td>{email}</td>
            <td>{phone}</td>
            <td>{age}</td>
            <td>{course}</td>
            <td>{course_format}</td>
            <td>{course_type}</td>
            <td>{status}</td>
            <td>{sum}</td>
            <td>{already_paid}</td>
            <td>{created_at}</td>
        </tr>
    );
};

export {Order};