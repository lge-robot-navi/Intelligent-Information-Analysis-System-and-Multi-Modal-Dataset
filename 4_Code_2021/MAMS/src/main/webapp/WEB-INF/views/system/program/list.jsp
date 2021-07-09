<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="custom" uri="/tags/custom-taglib" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:set var="REQUEST_CONTEXT_PATH" value="${pageContext.request.contextPath}" />

<link type="text/css" rel="stylesheet" href="${REQUEST_CONTEXT_PATH}/resources/js/jquery.fancytree/skin-bootstrap/ui.fancytree.css" />
<link type="text/css" rel="stylesheet" href="${REQUEST_CONTEXT_PATH}/resources/bootstrap-iconpicker/css/bootstrap-iconpicker.min.css" />
<style type="text/css">
  <!--
  .tree_div {}

  .tree_wrap {
    /* margin-top: 10px; min-height: 300px; max-height: 300px; overflow: auto; */
    border: gray 1px dotted;
  }

  ul.fancytree-container {
    min-height: 292px;
  }

  ul.fancytree-container {
    border: none;
  }

  #tree .fancytree-expander {
    padding-right: 1.3em;
  }

  #fancytree-drop-marker {
    background-color: orange !important;
  }

  /* iconpicker */
  .iconpicker {
    padding: 6px 12px;
  }
  -->
</style>

<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/js/jquery.fancytree/jquery.fancytree-all.js"></script>
<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/bootstrap-iconpicker/js/font-awesome/fa-icon-names-4.1.0.min.js"></script>
<script type="text/javascript" src="${REQUEST_CONTEXT_PATH}/resources/bootstrap-iconpicker/js/bootstrap-iconpicker.min.js"></script>
<script type="text/javascript">
  //<!--
  $(function () {

    allHide(); // Hide

    initTree(); // tree

    // 등록
    $('#btnAdd').attr('disabled', true).prop('disabled', true).click(function () {
      if ($(this).prop('disabled')) return false;
      fnAdd();
      return false;
    });
  });

  // 양식 숨기기
  function allHide() {
    /* $('#form').hide();
    $('#update').hide(); */
  }

  // 등록
  function fnAdd() {
    allHide();
    //$('#form').attr('src', 'form').show();
    var url = '${REQUEST_CONTEXT_PATH}/system/program/form';
    $('#form').empty().text('Loading...').load(url);
  }

  // 트리 데이터를 호출한다.
  function getChildren(upperPgmId, callback) {
    if (!upperPgmId) {
      upperPgmId = '1000';
    }
    $.ajax({
      async: false,
      dataType: 'json',
      url: '${REQUEST_CONTEXT_PATH}/system/program/json',
      data: {
        upperPgmId: upperPgmId
      },
      success: function (data) {
        callback(data);
      }
    });
  }

  // Tree 를 돌려준다.
  function getTree() {
    return $('#tree').fancytree('getTree');
  }

  // tree 초기화
  function initTree() {
    var _tree = $('#tree');
    _tree.fancytree({
      selectMode: 1,
      extensions: ["glyph", "dnd"],
      glyph: {
        map: {
          doc: "glyphicon glyphicon-file",
          docOpen: "glyphicon glyphicon-file",
          checkbox: "glyphicon glyphicon-unchecked",
          checkboxSelected: "glyphicon glyphicon-check",
          checkboxUnknown: "glyphicon glyphicon-share",
          error: "glyphicon glyphicon-warning-sign",
          expanderClosed: "glyphicon glyphicon-plus-sign",
          expanderLazy: "glyphicon glyphicon-plus-sign",
          // expanderLazy: "glyphicon glyphicon-expand",
          expanderOpen: "glyphicon glyphicon-minus-sign",
          // expanderOpen: "glyphicon glyphicon-collapse-down",
          folder: "glyphicon glyphicon-folder-close",
          folderOpen: "glyphicon glyphicon-folder-open",
          loading: "glyphicon glyphicon-refresh"
          // loading: "icon-spinner icon-spin"
        }
      },
      iconClass: function (node) {
        return "fa " + node.data.iconDs;
      },
      beforeActivate: function (event, data) {
        // for icon picker
        $('body').trigger('click');
      },
      activate: function (event, data) {
        getActivateEvent(data.node);
      },
      source: function () {

        var rootKey = '1000';

        // 초기 데이터를 호출한다.
        var children;
        getChildren(rootKey, function (data) {
          console.log('data => ', data);
          children = data;
        });

        var obj = [{
          key: rootKey,
          title: "ROOT",
          folder: true,
          expanded: true,
          children: children
        }];

        return obj;
      },
      lazyLoad: function (event, data) {
          data.result = {
            url: '${REQUEST_CONTEXT_PATH}/system/program/json',
            data: {
              upperPgmId: data.node.key
            },
            cache: false
          }
        } <
        c: if test = "${param.mode == 'T'}" > ,
      dnd: {
        preventVoidMoves: true,
        dragStart: function (node, data) {
          return true;
        },
        dragEnter: function (node, data) {
          if (node.parent !== data.otherNode.parent) {
            return false;
          }
          return ['before', 'after'];
        },
        dragDrop: function (node, data) {

          data.otherNode.moveTo(node, data.hitMode);

          var arUpperPgmId = new Array(),
            arPgmId = new Array(),
            arRankNo = new Array();

          // reordering
          var nodes = node.getParent().getChildren();
          $.each(nodes, function (i, item) {

            var num = i + 1;

            var rankNo = item.data.rankNo;
            item.data.rankNo = num;

            // set params
            if (rankNo != num) {
              arUpperPgmId.push(item.data.upperPgmId);
              arPgmId.push(item.data.pgmId);
              arRankNo.push(num);
            }
          });

          var params = $.param({
            upperPgmIds: arUpperPgmId,
            pgmIds: arPgmId,
            rankNos: arRankNo
          }, true);

          $.ajax({
            url: '${REQUEST_CONTEXT_PATH}/system/program/update-rank',
            async: false,
            data: params
          });

          return false;
        }
      } <
      /c:if>
    });
  }

  // getActiveNode
  function getActiveNode() {
    return $('#tree').fancytree('getActiveNode');
  }

  // onActivate
  function getActivateEvent(node) {

    allHide();

    // 등록버튼 활성화/비활성화
    $('#btnAdd').attr('disabled', !node.isFolder()).prop('disabled', !node.isFolder());

    var level = node.getLevel();
    if (level <= 1) { // Root
      return false;
    }

    var data = node.data;
    var pgmId = data.pgmId;
    var upperPgmId = data.upperPgmId;
    var url = '${REQUEST_CONTEXT_PATH}/system/program/update?upperPgmId=' + upperPgmId + '&pgmId=' + pgmId;
    //$('#update').attr('src', url).show();
    $('#form').empty().text('Loading...').load(url);
  }

  // Change Title
  function changeTitle(title, iconDs) {
    var node = getActiveNode();
    if (node) {
      node.setTitle(title);
      node.data.iconDs = iconDs;
      node.renderTitle();
    }
  }

  // reload
  function reloadParent() {
    var node = getActiveNode();
    var key = node.key;

    var parentNode = node.getParent();
    parentNode.load(true)
      .done(function () {
        tree = getTree();
        tree.activateKey(node.key);
        node = getActiveNode();
        node.setExpanded(true);
      });
  }

  // Append Child
  function appendChild(data) {

    var node = getActiveNode();
    if (node) {
      node.setActive(false);
      node.addNode({
        key: data.key,
        title: data.title,
        folder: data.folder,
        active: true,
        upperPgmId: data.upperPgmId,
        pgmId: data.pgmId,
        rankNo: data.rankNo,
        iconDs: data.iconDs
      });
      node.render();

      if (!node.isExpanded()) {
        node.setExpanded(true);
      }

      node = getActiveNode();
      node.render();

      getActivateEvent(node);
    }
  }
  //-->
</script>

<!-- button area -->
<div class="row margin-bottom">
  <div class="col-sm-12 text-align-right">
    <custom:auth type="CREATE">
      <div class="btn-group">
        <a id="btnAdd" href="${REQUEST_CONTEXT_PATH}/form" class="btn btn-sm btn-primary" role="button">
          <i class="fa fa-plus"></i>
          <spring:message code="common.insert" /></a>
      </div>
    </custom:auth>
  </div>
</div>
<!-- // button area -->

<p></p>

<div class="row">
  <div class="col-md-6">
    <div class="tree_wrap pre-scrollable">
      <div id="tree">
      </div>
    </div>
  </div>

  <div class="col-md-6">
    <div id="form"></div>
  </div>
</div>

<form id="frm" name="frm" method="post" action="${REQUEST_CONTEXT_PATH}/system/program/update-rank">
</form>