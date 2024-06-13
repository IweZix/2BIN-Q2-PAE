const clearPage = () => {
  const main = document.querySelector('main');
  main.innerHTML = '';
};

const renderPageTitle = (title) => {
  if (!title) return;
  document.title = title;
};

export { clearPage, renderPageTitle };
