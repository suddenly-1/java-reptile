package com.suddenly.util.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DownloadFile {
    /**
     * 导出数据到csv文件
     * @param file csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportCsv(File file, List<String> dataList) {
        boolean isSucess = false;
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out);
            // 解决中文乱码   加上UTF-8文件的标识字符
            out.write(new byte []{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF});
            bw = new BufferedWriter(osw);
            if (dataList != null && !dataList.isEmpty()) {
                for (String data : dataList) {
                    bw.append(data).append("\r");
                }
            }
            isSucess = true;
        } catch (Exception e) {
            isSucess = false;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                    bw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSucess;
    }


    /**
     * 导入csv数据
     * @param file csv文件(文件路径)
     * @return
     */
    public static List<String> importCsv(File file) throws FileNotFoundException, UnsupportedEncodingException {
        List<String> dataList = new ArrayList<String>();
        BufferedReader br = null;
        try {
//            br = new BufferedReader(new FileReader(file));
            // 解决中文乱码
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));
            String line = "";
            while ((line = br.readLine()) != null) {
//                int i = line.indexOf(",");
//                String substring = line.substring(i + 1);
//                dataList.add(substring);
                dataList.add(line);
            }
        } catch (Exception e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                    br = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataList;
    }
}
