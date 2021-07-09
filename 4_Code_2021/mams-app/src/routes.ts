// const Home = React.lazy(() => import("app/home/Home"));
// const ChkItmAgr = React.lazy(() => import("app/home/ChkItmAgr"));
// const FundHist = React.lazy(() => import("app/home/FundHist"));
// const CalcRgstUse = React.lazy(() => import("app/home/CalcRgstUse"));
// const RtrnRgst = React.lazy(() => import("app/home/RtrnRgst"));
// const NoticeBoard = React.lazy(() => import("app/bbs/NoticeBoard"));
// const NoticeBoardDetail = React.lazy(() => import("app/bbs/NoticeBoardDetail"));
// const ManualBoard = React.lazy(() => import("app/bbs/ManualBoard"));
// const ManualBoardDetail = React.lazy(() => import("app/bbs/ManualBoardDetail"));
// const AppInfo = React.lazy(() => import("app/info/AppInfo"));
// const Test = React.lazy(() => import("app/test/Test"));

import React from "react";

const AgentSettings = React.lazy(() => import("app/agentsettings/AgentSettings"));

const routeMenu = [
  { path: "/app/agent/settings", component: AgentSettings },
  //   { path: "/calc/agree", component: ChkItmAgr },
  //   { path: "/calc/hist", component: FundHist },
  //   { path: "/calc/calcrgstuse", component: CalcRgstUse },
  //   { path: "/calc/rtrnrgst", component: RtrnRgst },
  //   { path: "/bbs/notice", component: NoticeBoard },
  //   { path: "/bbs/noticedtl", component: NoticeBoardDetail },
  //   { path: "/bbs/manual", component: ManualBoard },
  //   { path: "/bbs/manualdtl", component: ManualBoardDetail },
  //   { path: "/info", component: AppInfo },
  //   { path: "/test", component: Test },
];

export default routeMenu;
