import { ACTION_TYPES } from "store/ActionTypes";

const initState = {
  menuOpen: false,
};

const reducerMain = (state = initState, action) => {
  switch (action.type) {
    case ACTION_TYPES.MAIN.MENU_OPEN:
      return {
        ...state,
        menuOpen: true,
      };
    case ACTION_TYPES.MAIN.MENU_CLOSE:
      return {
        ...state,
        menuOpen: false,
      };

    default:
      return state;
  }
};

export default reducerMain;
