import React, { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from "../../hooks/reduxHooks";
import { orderAction } from "../../redux/slices/ordersSlice";
import { Order } from "./Order";
// @ts-ignore
import style from "./Order.module.css";
import { Pagination } from "../Pagination/Pagination";

const columns = [
    { key: 'id', label: 'ID' },
    { key: 'name', label: 'Name' },
    { key: 'surname', label: 'Surname' },
    { key: 'email', label: 'Email' },
    { key: 'phone', label: 'Phone' },
    { key: 'age', label: 'Age' },
    { key: 'course', label: 'Course' },
    { key: 'course_format', label: 'Course Format' },
    { key: 'course_type', label: 'Course Type' },
    { key: 'status', label: 'Status' },
    { key: 'sum', label: 'Sum' },
    { key: 'already_paid', label: 'Already Paid' },
    { key: 'created_at', label: 'Created At' },
];

const Orders = () => {
    const [searchParams, setSearchParams] = useSearchParams();
    const { order, itemsFound } = useAppSelector(state => state.order);
    const dispatch = useAppDispatch();

    const pageNumber = Number(searchParams.get('page') || 1);
    const sortedBy = searchParams.get('sortedBy') || 'id';
    const orderDirection = searchParams.get('order') || 'asc';
    const itemsPerPage = 25;

    useEffect(() => {
        dispatch(orderAction.getAll({ query: { page: pageNumber.toString(), sortedBy, order: orderDirection } }));
    }, [dispatch, pageNumber, sortedBy, orderDirection]);

    const updateQueryParams = (updates: Record<string, string>) => {
        setSearchParams({
            page: searchParams.get('page') || '1',
            ...Object.fromEntries(searchParams.entries()),
            ...updates
        });
    };

    const handleSortChange = (column: string) => {
        const newOrder = sortedBy === column && orderDirection === 'asc' ? 'desc' : 'asc';
        updateQueryParams({ sortedBy: column, order: newOrder });
    };

    const renderSortIcon = (column: string) =>
        sortedBy === column ? (orderDirection === 'asc' ? '🔼' : '🔽') : null;

    const pageCount = Math.ceil(itemsFound / itemsPerPage);

    return (
        <div className={style.ordersContainer}>
            <table className={style.ordersTitle}>
                <thead>
                <tr>
                    {columns.map(({ key, label }) => (
                        <th key={key} onClick={() => handleSortChange(key)}>
                            {label} {renderSortIcon(key)}
                        </th>
                    ))}
                </tr>
                </thead>
                <tbody>
                {order.map(orderItem => (
                    <Order order={orderItem} key={orderItem.id} />
                ))}
                </tbody>
            </table>
            <div className={style.paginationContainer}>
                <Pagination
                    pageCount={pageCount}
                    currentPage={pageNumber}
                    onPageChange={(newPage) => updateQueryParams({ page: newPage.toString() })}
                />
            </div>
        </div>
    );
};

export { Orders };
