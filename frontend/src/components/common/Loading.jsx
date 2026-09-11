function Loading({ message = "Loading..." }) {
  return (
    <div className="loading" role="status" aria-live="polite">
      {message}
    </div>
  );
}

export default Loading;
