import { combineReducers } from "redux";
import reducerMain from "./reducerMain";

const reducers = combineReducers({
  main: reducerMain,
});

export default reducers;
