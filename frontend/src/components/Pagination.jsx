function Pagination({
  pageNo,
  pageSize,
  totalPages,
  totalElements,
  onPageChange,
  onPageSizeChange,
}) {
  return (
    <div className="pagination-container">
      <div className="pagination-page-size">
        <label>Rows per page: </label>

        <select
          value={pageSize}
          onChange={(event) => onPageSizeChange(Number(event.target.value))}
        >
          <option value={5}>5</option>
          <option value={10}>10</option>
          <option value={20}>20</option>
        </select>
      </div>

      <div className="pagination-total">
        <span>Total: {totalElements}</span>
      </div>

      <div className="pagination-navigation">
        <button
          type="button"
          disabled={pageNo === 0}
          onClick={() => onPageChange(pageNo - 1)}
        >
          Previous
        </button>

        <span>
          Page {totalPages === 0 ? 0 : pageNo + 1} of {totalPages}
        </span>

        <button
          type="button"
          disabled={totalPages === 0 || pageNo >= totalPages - 1}
          onClick={() => onPageChange(pageNo + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
}

export default Pagination;
