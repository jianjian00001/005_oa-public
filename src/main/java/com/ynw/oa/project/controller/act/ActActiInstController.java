package com.ynw.oa.project.controller.act;

import com.ynw.oa.common.utils.StringUtils;
import com.ynw.oa.framework.web.controller.BaseController;
import com.ynw.oa.project.po.ActHiActinst;
import com.ynw.oa.project.po.User;
import com.ynw.oa.project.service.ACT.hiActInst.IActHiActInstService;
import com.ynw.oa.project.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 俞能武
 */
@Controller
@RequestMapping("/hiActi")
public class ActActiInstController extends BaseController {

    private final static String prefix = "system/actHIActi/";
    @Autowired
    IActHiActInstService iActHiActInstService;

    @Autowired
    IUserService iUserService;


    /**
     *
     * @描述: 通过实例Id 获取该活动信息
     *
     * @params:
     * @return:
     * @date: 2020/4/23 20:50
     */
    @RequestMapping("/AjaxHiProcActiList/{procInstId}")
    @ResponseBody
    public List<ActHiActinst> HiProcActiList(@PathVariable("procInstId") String procInstId)
    {
        List<ActHiActinst> actHiActinsts = iActHiActInstService.selectByByProcInstId(procInstId);
        return actHiActinsts;
    }


    /**
     *
     * @描述: 通过实例Id 获取该活动信息 查看过程
     *
     * @params:
     * @return:
     * @date: 2020/4/23 20:50
     */
    @RequestMapping("/HiProcActiList/{procInstId}")
    public String ActiList(@PathVariable("procInstId") String procInstId, Model model)
    {
        List<ActHiActinst> actHiActinsts = iActHiActInstService.selectByByProcInstId(procInstId);

        for (ActHiActinst actinst : actHiActinsts)
        {
            //将用户Id 设为用户名，展示给页面
            if (!StringUtils.isEmpty(actinst.getAssignee()))
            {
                User user = iUserService.selectByPrimaryKey(actinst.getAssignee());
                actinst.setAssignee(user.getName());
            }
        }

        model.addAttribute("actHiActinsts", actHiActinsts);
        return prefix + "actHiActi";
    }
}
