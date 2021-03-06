package com.hbyd.parks.client.attendancesys.action;

import com.google.gson.Gson;
import com.hbyd.parks.client.util.JsonHelper;
import com.hbyd.parks.dto.attendancesys.LeaveTypeDTO;
import com.hbyd.parks.common.model.AjaxMessage;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.ws.attendancesys.LeaveTypeWS;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by len on 14-8-4.
 */
@Controller
@Scope("prototype")
public class LeaveTypeAction extends ActionSupport implements ModelDriven<QueryBeanEasyUI> {
    private QueryBeanEasyUI page = new QueryBeanEasyUI();
    //搜索提交的参数
    private String key;
    private LeaveTypeDTO leaveType;
    private String name;
    private String leaveTypeId;
    @Resource
    private LeaveTypeWS leaveTypeWS;

    @Override
    public QueryBeanEasyUI getModel() {
        return page;
    }

    private Gson gson = new Gson();

    public String leaveTypeList() {
        String hql = "where name like ?";
        List params = new ArrayList();
        params.add(key + "%");

        PageBeanEasyUI list = key == null ? leaveTypeWS.getPageBean(page, "", null) : leaveTypeWS.getPageBean(page, hql, params.toArray());
        //如果rows为null，需要实例化一下。如果不实例化，序列化为json时没有rows属性，前段的数据不会更新。
        if (list.getRows() == null) list.setRows(new ArrayList());
        String result = gson.toJson(list);
        JsonHelper.writeJson(result);
        return null;
    }

    public String getAllLeaveType() {
        List<LeaveTypeDTO> list = leaveTypeWS.findAll();
        String result =list==null?"{}": gson.toJson(list);

        JsonHelper.writeJson(result);
        return null;
    }

    public String addLeaveType() {
        AjaxMessage message = new AjaxMessage();
        try {
            leaveTypeWS.save(leaveType);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("新建失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }

        return null;
    }

    public String editLeaveType() {
        AjaxMessage message = new AjaxMessage();
        try {
            leaveTypeWS.update(leaveType);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("编辑失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }
        return null;
    }

    public String deleteLeaveType() {
        AjaxMessage message = new AjaxMessage();
        try {
            leaveTypeWS.delByID(leaveTypeId);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMessage("删除失败");
        } finally {
            String result = gson.toJson(message);
            JsonHelper.writeJson(result);
        }


        return null;
    }

    public String validateNameExist() {

        return null;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LeaveTypeDTO getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveTypeDTO leaveType) {
        this.leaveType = leaveType;
    }

    public String getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }
}


