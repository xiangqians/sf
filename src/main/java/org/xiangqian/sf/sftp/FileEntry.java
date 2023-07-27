package org.xiangqian.sf.sftp;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 文件条目
 * <p>
 * 示例：
 * drwxr-xr-x    2 root     root         4096 Jul  7 15:47 test
 * 解析：
 * type=d
 * mod=[rwx][r-x][r-x]
 * count=2
 * owner=root
 * group=root
 * size=4096
 * lastModifiedDate=Jul  7 15:47
 * name=test
 *
 * @author xiangqian
 * @date 01:38 2022/07/24
 */
@Data
public class FileEntry {

    // 文件类型
    // -：普通文件
    // d：目录文件
    // p：管理文件
    // l；链接文件
    // b：块设备文件
    // c：字符设备文件
    // s：套接字文件
    private String type;

    // mode，文件权限
    // r：读权限
    // w：写权限
    // x：可执行权限
    // -：无权限
    // [第1组]：拥有者权限
    // [第2组]：组用户权限
    // [第3组]：其他用户权限
    private String mod;

    // 对于普通文件：链接数
    // 对于目录文件：第一级子目录数
    private Integer count;

    // 拥有者
    private String owner;

    // 组
    private String group;

    // 文件大小
    private String size;

    // 最后修改时间
    private String lastModifiedDate;

    // 文件名
    private String name;

    @Override
    public String toString() {
        return type + mod + '\t' + count + '\t' + owner + '\t' + group + '\t' + size + '\t' + lastModifiedDate + '\t' + name;
    }

    public static FileEntry parse(String text) {
        if (StringUtils.isEmpty(text = StringUtils.trim(text))) {
            return null;
        }

        String[] array = text.split("\\s+");
        if (array.length != 9) {
            return null;
        }

        FileEntry fileEntry = new FileEntry();
        fileEntry.setType(array[0].substring(0, 1));
        fileEntry.setMod(array[0].substring(1));
        fileEntry.setCount(NumberUtils.toInt(array[1], -1));
        fileEntry.setOwner(array[2]);
        fileEntry.setGroup(array[3]);
        fileEntry.setSize(array[4]);
        fileEntry.setLastModifiedDate(StringUtils.join(array, " ", 5, 8));
        fileEntry.setName(array[8]);
        return fileEntry;
    }

}
