import { createMuiTheme, CssBaseline, ThemeProvider } from "@material-ui/core";
import React, { Suspense } from "react";
import { Route, Switch } from "react-router-dom";
import Main from "./layout/Main";

const theme = createMuiTheme({
  overrides: {
    MuiCssBaseline: {
      "@global": {},
    },
    MuiButton: {
      //disableElevation: true,
    },
  },
  props: {
    // Name of the component ⚛️
    MuiButtonBase: {
      // The default props to change
      //disableRipple: true, // No more ripple, on the whole application 💣!
    },
    MuiButton: {
      //disableElevation: true,
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Suspense fallback={<div>Loading...</div>}>
        <Switch>
          <Route path="/" component={Main} />
        </Switch>
      </Suspense>
    </ThemeProvider>
  );
}

export default App;
