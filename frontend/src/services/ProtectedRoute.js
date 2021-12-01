import React from "react";
import { Route, Redirect } from "react-router-dom";
import auth from "./Auth";

export const ProtectedRoute = ({ component: Component, accessValid, ...rest }) => {

  return (
    <Route
      {...rest}
      render={(props) => {
        if (auth.isAuthenticated()) {
          if (accessValid !== undefined ? accessValid() : false) {
            return <Component {...props} />;
          } else {
            return (<Redirect
              to={"/home/" + auth.user.username}
            />);
          }       
        } else {
          return (
            <Redirect
              to={{
                pathname: "/",
                state: {
                  from: props.location,
                },
              }}
            />
          );
        }
      }}
    />
  );
};
