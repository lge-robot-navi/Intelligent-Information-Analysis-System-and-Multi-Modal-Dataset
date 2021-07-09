import React from "react";
import { Route } from "react-router-dom";
import routes from "routes";

interface Props {}

export default function Main(props: Props) {
  const getRoutes = (routes: any) => {
    return routes.map((prop: any, key: any) => {
      return <Route path={prop.path} component={prop.component} key={key} />;
    });
  };

  return <React.Fragment>{getRoutes(routes)}</React.Fragment>;
}
