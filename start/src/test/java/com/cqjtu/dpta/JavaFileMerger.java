package com.cqjtu.dpta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JavaFileMerger {

    /**
     * 将指定目录下所有的 .java 文件内容合并到一个 .txt 文件中。
     *
     * @param sourceDirPath 源文件夹路径，用于查找 .java 文件
     * @param outputPath    输出 .txt 文件的完整路径
     */
    public static void mergeJavaFiles(String sourceDirPath, String outputPath) {
        Path sourceDir = Paths.get(sourceDirPath);
        Path outputFile = Paths.get(outputPath);

        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {

            // 1. 查找并排序所有 .java 文件
            List<Path> javaFiles = Files.walk(sourceDir)
                    .filter(Files::isRegularFile) // 确保是文件，不是目录
                    .filter(path -> path.toString().endsWith(".java"))
                    .sorted() // 排序，保证结果稳定
                    .collect(Collectors.toList());

            System.out.println("找到 " + javaFiles.size() + " 个 .java 文件。");

            // 2. 遍历每个 .java 文件并写入输出文件
            for (Path javaFile : javaFiles) {
                // 写入文件分隔符和文件名，方便识别
                writer.write("\n---------- File: " + javaFile.toString() + " ----------\n");

                // 读取当前 .java 文件的内容并写入
                try (BufferedReader reader = Files.newBufferedReader(javaFile, StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine(); // 添加换行符
                    }
                } catch (IOException e) {
                    System.err.println("读取文件失败: " + javaFile.toString() + ", 错误: " + e.getMessage());
                    // 可以选择跳过该文件，继续处理下一个
                }
            }

            System.out.println("所有 .java 文件内容已成功合并到: " + outputFile.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("操作文件时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // --- 请修改这里的路径 ---
        String sourceDirectory = "D:\\code\\dpta\\dpta-dao\\src\\main\\java\\com\\cqjtu\\dpta\\dao\\entity"; // 示例：从当前项目根目录下的 src 文件夹查找
        String outputFilePath = "./merged_java_files.txt"; // 示例：将结果保存到当前目录下的 merged_java_files.txt
        // --- 修改结束 ---

        // 如果命令行参数提供了源目录和输出文件，则使用它们
        if (args.length >= 2) {
            sourceDirectory = args[0];
            outputFilePath = args[1];
        } else if (args.length == 1 || args.length > 2) {
            System.err.println("用法: java JavaFileMerger <源文件夹路径> <输出文件路径>");
            return; // 参数不正确时退出
        }

        System.out.println("开始扫描目录: " + sourceDirectory);
        System.out.println("输出文件: " + outputFilePath);

        mergeJavaFiles(sourceDirectory, outputFilePath);
    }
}