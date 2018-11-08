package com.arley.cms.console.util;

import com.arley.cms.console.pojo.vo.SysPermissionVO;
import com.arley.cms.console.pojo.vo.TreeVO;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单工具类
 *
 * @author chen
 * @date 2018-01-02 17:54
 */
public class MenuUtils {


    /**
     * 判断菜单是否被勾选
     * @param originMenus 原菜单
     * @return
     */
    public static Set<TreeVO> makeXTreeList(List<SysPermissionVO> originMenus){
        Set<TreeVO> trees = new LinkedHashSet <>();
        boolean disabled;
        for(SysPermissionVO sysPermission : originMenus){
            disabled = sysPermission.getMenuState() != 1;
            trees.add(new TreeVO(sysPermission.getPermissionId(), sysPermission.getMenuName(), sysPermission.getParentId(), disabled));
        }
        return eachTree(trees);
    }

    /**
     * 将已转成Tree对象的list进行转换成树状
     */
    private static Set<TreeVO> eachTree(Set<TreeVO> trees){
        Set<TreeVO> rootTrees = new LinkedHashSet<>();
        for (TreeVO tree : trees) {
            if(tree.getParentId() == null){
                rootTrees.add(tree);
            }
            for (TreeVO t : trees) {
                if(tree.getId().equals(t.getParentId())){
                    if(tree.getChildren().size() == 0){
                        List<TreeVO> myChildrens = new ArrayList<>();
                        myChildrens.add(t);
                        tree.setChildren(myChildrens);
                    }else{
                        tree.getChildren().add(t);
                    }
                }
            }
        }
        return rootTrees;
    }

}