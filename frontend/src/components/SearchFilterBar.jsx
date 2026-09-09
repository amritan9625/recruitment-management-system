function SearchFilterBar({ children, onReset }) {
  return (
    <div className="search-filter-bar">
      <div className="search-filter-controls">{children}</div>

      <button type="button" onClick={onReset} className="search-filter-reset">
        Reset
      </button>
    </div>
  );
}

export default SearchFilterBar;
