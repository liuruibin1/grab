package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
*
* ${table.comment!} 服务类
* @author robinson
* @date ${date}
* @tags 我爱的人在很远的地方, 我必须更加努力
*/
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

}
</#if>
