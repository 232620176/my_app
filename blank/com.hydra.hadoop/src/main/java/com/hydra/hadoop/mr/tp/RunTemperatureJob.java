package com.hydra.hadoop.mr.tp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunTemperatureJob {
    private static DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {
        String name = "temperature";
        String[] path = { name, name };
        //三个年份，故而需要三个reduce
        MRUtil.runTemplateWithBasePath(name, 3, new Callable() {
            @Override
            public void initJob(Job job) {
                job.setJarByClass(RunTemperatureJob.class);

                job.setMapperClass(TemperatureMapper.class);
                job.setReducerClass(TemperatureReduce.class);

                job.setMapOutputKeyClass(KeyPair.class);
                job.setMapOutputValueClass(Text.class);

                job.setGroupingComparatorClass(GroupTemperature.class);
                job.setPartitionerClass(TemperaturePartition.class);
                job.setSortComparatorClass(SortTemperature.class);
            }
        }, path);
    }

    static class TemperatureMapper extends Mapper<LongWritable, Text, KeyPair, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] ss = line.split("\t");
            if (2 == ss.length) {
                try {
                    Date d = DF.parse(ss[0]);
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    int year = c.get(Calendar.YEAR);
                    String temperature = ss[1].substring(0, ss[1].indexOf("℃"));
                    KeyPair kp = new KeyPair();
                    kp.setTemperature(Integer.valueOf(temperature));
                    kp.setYear(year);
                    context.write(kp, value);
                } catch (Exception e) {
                    log.error("{}", e);
                }
            }
        }
    }

    static class TemperatureReduce extends Reducer<KeyPair, Text, KeyPair, Text> {
        @Override
        protected void reduce(KeyPair kp, Iterable<Text> value, Context context)
                throws IOException, InterruptedException {
            for (Text v : value) {
                context.write(kp, v);
            }
        }
    }
}
