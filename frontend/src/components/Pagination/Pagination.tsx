import React from "react";
// @ts-ignore
import ReactPaginate from "react-paginate";
// @ts-ignore
import style from "./Pagination.module.css";

interface IPaginationProps {
    pageCount: number;
    currentPage: number;
    onPageChange: (selectedPage: number) => void;
}

const Pagination: React.FC<IPaginationProps> = ({ pageCount, currentPage, onPageChange }) => {
    return (
        <ReactPaginate
            previousLabel={"Prev"}
            nextLabel={"Next"}
            breakLabel={"..."}
            pageCount={pageCount}
            marginPagesDisplayed={1}
            pageRangeDisplayed={4}
            onPageChange={(event) => onPageChange(event.selected + 1)}
            forcePage={currentPage - 1}
            containerClassName={style.pagination}
            pageClassName={style.pageItem}
            activeClassName={style.activePage}
            previousClassName={`${style.pageItem} ${currentPage === 1 ? style.hidden : ""}`}
            nextClassName={`${style.pageItem} ${currentPage === pageCount ? style.hidden : ""}`}
            disabledClassName={style.disabledPage}
            breakClassName={style.breakItem}
        />
    );
};

export { Pagination };
