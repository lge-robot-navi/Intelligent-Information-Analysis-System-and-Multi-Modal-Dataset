import { AppBar, Box, Button, Card, CardContent, Checkbox, Radio, FormControlLabel, Grid, FormControl } from "@material-ui/core";
import { makeStyles, Theme } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import axios from "axios";
import { IfTaCode } from "if/TaCode";
import React, { useEffect, useState } from "react";
import * as RJSON from "really-relaxed-json";

interface Props {}

const useStyles = makeStyles((theme: Theme) => ({
  appBar: { padding: "10px" },
  root: { paddingTop: "55px" },
  card: { margin: "6px" },
  item: { padding: "3px" },
}));

const AgentSettings = (props: Props) => {
  const classes = useStyles();
  const [listPh, setListPh] = useState([] as Array<IfTaCode>);
  const [listGw, setListGw] = useState([] as Array<IfTaCode>);

  useEffect(() => {
    getData();
    // eslint-disable-next-line
  }, []);

  const getDataPh = () => {
    axios
      .get("/monitoring/api/codeInfoAll?cdgrpCd=TA009")
      .then((res) => {
        console.log("res is", res);
        res.data.forEach((ele) => {
          ele.ds = JSON.parse(RJSON.toJson(ele.codeDs));
        });
        setListPh(res.data);
      })
      .catch((e) => {
        console.error("E", e);
      });
  };

  const getDataGw = () => {
    axios
      .get("/monitoring/api/codeInfoAll?cdgrpCd=TA010")
      .then((res) => {
        console.log("res is", res);
        res.data.forEach((ele) => {
          ele.ds = JSON.parse(RJSON.toJson(ele.codeDs));
        });
        setListGw(res.data);
      })
      .catch((e) => {
        console.error("E", e);
      });
  };

  const getData = () => {
    getDataPh();
    getDataGw();
  };

  const doSavePh = axios.post("/monitoring/api/save/agent/useyn", listPh);
  const doSaveGw = axios.post("/monitoring/api/save/agent/useyn", listGw);

  const handleSave = (e) => {
    axios
      .all([doSavePh, doSaveGw])
      .then(axios.spread((resPh, resGw) => {
          console.log("resPh is", resPh);
          console.log("resGw is", resGw);

          if (typeof resPh !== "undefined" && typeof resGw !== "undefined") {
            axios
            .get("/monitoring/api/refresh/agent/settings")
            .then((res) => {
              console.log("res is", res);
              window.alert("설정 완료 되었습니다");
            })
            .catch((e) => {
              console.error("E", e);
              window.alert("설정 갱신이 실패 되었습니다");
            });
          }
        }))
      .catch((e) => {
        console.error("E", e);
        window.alert("설정 저장이 실패 되었습니다");
      })
  };

  const handleItmChgValPh = (ele, prop, val) => {
    ele[prop] = val;
    console.log("ele", ele, val);
    setListGw(listGw.map((ele) => ele));
  };

  const handleItmChgValGw = (ele, prop, val) => {
    ele[prop] = val;
    setListPh(listPh.map((ele) => ele));
  };

  const handleMoveChange = (elePh, eleGw, val) => {
    if (val === "P") {
      elePh.useYn = "Y";
      eleGw.useYn = "N";
    } else if (val === "G") {
      elePh.useYn = "N";
      eleGw.useYn = "Y";
    } else {
      elePh.useYn = val;
      eleGw.useYn = val;
    }
    console.log("elePh, eleGw, val ===> ", elePh, eleGw, val);
    setListPh(listPh.map((elePh) => elePh));
    setListGw(listGw.map((eleGw) => eleGw));
  };

  /* 이동형 Agent 설정 */
  const renderMove = () => {
    let render = [];
    if (listPh.length === listGw.length) {
      for (let i = 0; i < listPh.length; i++) {
        if (listPh[i].ds.fixed === "N") {
          render.push(
            <React.Fragment key={listPh[i].codeCd}>
              <div className={classes.item}>
                <FormControl component="fieldset">
                  <div>
                    <b>{listPh[i].ds.fixed === "N" ? "이동형" : null} (Agent ID : {listPh[i].ds.agentId})&nbsp;&nbsp;&nbsp;&nbsp;</b>
                    <FormControlLabel
                      label="미사용"
                      control={
                        <Radio
                          value="N"
                          checked={listPh[i].useYn === "N" && listGw[i].useYn === "N" ? true : false}
                          onChange={(e) => handleMoveChange(listPh[i], listGw[i], e.target.value)}
                        />
                      }
                    />
                    <FormControlLabel
                      label="포항"
                      control={
                        <Radio
                          value="P"
                          checked={listPh[i].useYn === "Y" ? true : false}
                          onChange={(e) => handleMoveChange(listPh[i], listGw[i], e.target.value)}
                        />
                      }
                          />
                    <FormControlLabel
                      label="광주"
                      control={
                        <Radio
                          value="G"
                          checked={listGw[i].useYn === "Y" ? true : false}
                          onChange={(e) => handleMoveChange(listPh[i], listGw[i], e.target.value)}
                        />
                      }
                    />
                  </div>
                </FormControl>
              </div>
            </React.Fragment>
          )
        }
      }
    }
    return render;
  }

  /* 고정형 Agent 설정 */
  const renderFixed = () => {
    let render = [];
    if (listPh.length === listGw.length) {
      for (let i = 0; i < listPh.length; i++) {
        if (listPh[i].ds.fixed === "Y" || listPh[i].ds.fixed === "B") {
          render.push(
            <React.Fragment key={listPh[i].codeCd}>
              <div className={classes.item}>
                <FormControl component="fieldset">
                  <div>
                    <b>{listPh[i].ds.fixed === "Y" ? "고정형" : "Drone"} (Agent ID : {listPh[i].ds.agentId})&nbsp;&nbsp;&nbsp;&nbsp;</b>
                    <FormControlLabel
                      label="포항"
                      control={
                        <Checkbox
                          checked={listPh[i].useYn === "Y" ? true : false}
                          onChange={(e) => handleItmChgValPh(listPh[i], "useYn", e.target.checked ? "Y" : "N")}
                        />
                      }
                    />
                    <FormControlLabel
                      label="광주"
                      control={
                        <Checkbox
                          checked={listGw[i].useYn === "Y" ? true : false}
                          onChange={(e) => handleItmChgValGw(listGw[i], "useYn", e.target.checked ? "Y" : "N")}
                        />
                      }
                    />
                  </div>
                </FormControl>
              </div>
            </React.Fragment>
          );
        }
      }
    }
    return render;
  }
  
  return (
    <div className={classes.root}>
      <AppBar position="fixed" className={classes.appBar}>
        <Typography variant="h6">Agent Use Settings</Typography>
      </AppBar>

      <Box className={""}>
        <Card className={classes.card}>
          <CardContent>
            <Button variant="contained" color="primary" onClick={(e) => handleSave(e)}>
              설정 저장
            </Button>
          </CardContent>
        </Card>
      </Box>
      <Grid container>
        <Grid item xs={6}>
          <Card className={classes.card}>
            <CardContent>
              <Typography variant="h6">이동형 Agent 설정</Typography>
              <div>{renderMove()}</div>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={6}>
          <Card className={classes.card}>
            <CardContent>
              <Typography variant="h6">고정형 Agent 설정</Typography>
              <div>{renderFixed()}</div>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </div>
  );
};

export default AgentSettings;
