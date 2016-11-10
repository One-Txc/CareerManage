package com.control;

import com.ResObj.RecruitResObj;
import com.pojo.CmArea;
import com.pojo.CmCompany;
import com.pojo.CmJob;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by w on 2016/10/31.
 */
@Controller
public class RecruitCtrl {
    @Autowired
    private RecruitService recruitService;
    @Autowired
    private InterService interService;
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AreaService  areaService;

    //获取所有招聘信息列表——ly
    @RequestMapping(value = "/recruit/findAllRecruits",method = RequestMethod.GET )
    public String findAll(ModelMap modelMap){
        List<RecruitResObj> recruitList = recruitService.findAll();
        modelMap.addAttribute("recruitList",recruitList);
        //查询面试人数
        int InterCount = interService.findByRidCount(1);
        modelMap.addAttribute("InterCount",InterCount);
        return "system/meeting/selectAllMeeting";
    }

    //增加前——ly
    @RequestMapping(value = "/recruit/addpro",method = RequestMethod.GET )
    public String addpro(ModelMap modelMap){
        List<CmCompany> companyList = companyService.FindALLCompany();
        modelMap.addAttribute("companyList",companyList);
        List<CmArea> areaList = areaService.findAllArea();
        modelMap.addAttribute("areaList",areaList);
        List<CmJob> jobList = jobService.findAll();
        modelMap.addAttribute("jobList",jobList);
        return "system/meeting/MeetAdd";
    }

    //增加招聘信息——ly
    @RequestMapping(value = "/recruit/addRecruit",method = RequestMethod.POST )
    public String addRecruit(int cid,int aid,int jid,int rsalary,Boolean rsex,int rnum,String rend,String rinfo,ModelMap modelMap) throws ParseException {
        boolean ResMsg = recruitService.addRecruit(cid, aid, jid, rsalary, rsex, rnum, rend,rinfo);
        if(ResMsg){
            modelMap.addAttribute("ResMsg","添加成功！");
        }else{
            modelMap.addAttribute("ResMsg","添加失败！");
        }
        return "redirect:/recruit/findAllRecruits";
    }

    //删除招聘信息——ly
    @RequestMapping(value = "/recruit/delRecruit",method = RequestMethod.GET )
    public String delRecruit(int rid,ModelMap modelMap) throws ParseException {
        boolean ResMsg = recruitService.delRecruit(rid);
        if(ResMsg){
            modelMap.addAttribute("ResMsg","删除成功！");
        }else{
            modelMap.addAttribute("ResMsg","删除失败！");
        }
        return "redirect:/recruit/findAllRecruits";
    }

    //编辑前——ly
    @RequestMapping(value = "/recruit/findByRid",method = RequestMethod.GET )
    public String findByRid(int rid, ModelMap modelMap){
        RecruitResObj recruit = recruitService.findByRid2(rid);
        modelMap.addAttribute("recruit",recruit);
        List<CmCompany> companyList = companyService.FindALLCompany();
        modelMap.addAttribute("companyList",companyList);
        List<CmArea> areaList = areaService.findAllArea();
        modelMap.addAttribute("areaList",areaList);
        List<CmJob> jobList = jobService.findAll();
        modelMap.addAttribute("jobList",jobList);
        System.out.println("findByRid-----"+recruit.getCname());
        return "system/meeting/MeetUpdate";
    }

    //编辑招聘信息——ly
    @RequestMapping(value = "/recruit/updateRecruit",method = RequestMethod.POST )
    public String updateRecruit(int rid,int cid,int aid,int jid,int rsalary,Boolean rsex,int rnum,String rend,String rinfo, ModelMap modelMap) throws ParseException {
        boolean ResMsg = recruitService.updateRecruit(rid, cid, aid, jid, rsalary, rsex, rnum, rend,rinfo);
        if(ResMsg){
            modelMap.addAttribute("ResMsg","编辑成功！");
        }else{
            modelMap.addAttribute("ResMsg","编辑失败！");
        }
        return "redirect:/recruit/findAllRecruits";
    }

    //搜索招聘信息——ly
    @RequestMapping(value = "/recruit/query",method = RequestMethod.POST )
    public String query(int type, String searchtext, ModelMap modelMap){
        List<RecruitResObj> recruitList = new ArrayList<RecruitResObj>();
        if(type==0){
            recruitList = recruitService.findByCname(searchtext);
            modelMap.addAttribute("recruitList",recruitList);
        }else if(type==1){
            //按招聘岗位？
        }
        return "system/meeting/MeetSearch";
    }

}