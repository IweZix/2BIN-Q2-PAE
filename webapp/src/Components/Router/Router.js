import routes from './routes';
import Navigate from "./Navigate";

const Router = () => {
  onFrontendLoad();
  onNavBarClick();
  onHistoryChange();
};

function onNavBarClick() {
  const navItems = document.querySelectorAll('#navbarWrapper');

  navItems.forEach((item) => {
    item.addEventListener('click', (e) => {
      e.preventDefault();
      let {target} = e;

      while (target && !target.dataset.uri) {
        target = target.parentElement;
      }
      if (!target) return;

      const { uri } = target.dataset; // Use object destructuring here
      const componentToRender = routes[uri];

      if (!componentToRender) throw Error(`The ${uri} ressource does not exist.`);

      componentToRender();
      window.history.pushState({}, '', uri);
    });
  });
}

function onHistoryChange() {
  window.addEventListener('popstate', () => {
    const uri = window.location.pathname;
    const componentToRender = routes[uri];
    componentToRender();
  });
}

function onFrontendLoad() {
  window.addEventListener('load', () => {
    const uri = window.location.pathname;
    const componentToRender = routes[uri];
    if (!componentToRender)
      Navigate('/404');
    else
      componentToRender();
  });
}

export default Router;
