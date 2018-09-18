package com.hydra.hadoop.mr.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MRUtil {
    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDateStr() {
        return getDateStr(new Date());
    }

    public static String getDateStr(Date date) {
        return DF.format(date);
    }

    public static String[] mergeArray(String[] args, String[] path) {
        String[] param = new String[args.length + path.length];
        System.arraycopy(args, 0, param, 0, args.length);
        System.arraycopy(path, 0, param, args.length, path.length);
        return param;
    }

    public static void runTemplateWithBasePath(String tkName, Callable ca, String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        runTemplateWithBasePath(tkName, 1, ca, args);
    }

    public static void runTemplateWithBasePath(String tkName, int tkNum, Callable ca, String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        if (null == args || args.length < 2) {
            log.error("Usage: " + tkName + " <in> [<in>...] <out>");
            System.exit(2);
        }
        int len = args.length;
        String[] path = new String[len];
        for (int i = 0; i < len - 1; ++i) {
            path[i] = BASE_INPUT_PATH + args[i];
        }
        path[len - 1] = BASE_OUTPUT_PATH + args[len - 1];
        runTemplate(tkName, tkNum, ca, path);
    }

    public static void runTemplate(String tkName, Callable ca, String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        runTemplate(tkName, 1, ca, args);
    }

    public static void runTemplate(String tkName, int tkNum, Callable ca, String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        int len = otherArgs.length;
        if (len < 2) {
            log.error("Usage: " + tkName + " <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, tkName);
        ca.initJob(job);
        job.setNumReduceTasks(tkNum);//设置reduce任务的个数
        //mapreduce输入数据所在目录
        for (int i = 0; i < len - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        //mr执行之后的输出数据的目录
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[len - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    public static interface Callable {
        public void initJob(Job job);
    }

    public static final String BASE_INPUT_PATH  = "/usr/input/";
    public static final String BASE_OUTPUT_PATH = "/usr/output/";

    private MRUtil() {
        throw new UnsupportedOperationException("Construct MRUtil...");
    }
}
