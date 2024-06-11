package com.example.xiaomi11.test

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
//        println("""全民制作人大家好，我是练习时长两年半的安卓练习生孙振瑜。
//喜欢唱、跳、rap、篮球。""")
        val name = "A"
        val role = "student"
        val greeting = "He1llo! My name is $name. I am $role."
        println(greeting)
        // 控制台计算器
        while (true) {
            println("请输入数字A:")
            val A = readln().toFloat();

            println("请输入运算符(+, -, *, /):")
            val mOperator = readln();

            println("请输入数字B:")
            val B = readln().toFloat();
            val mResult = when (mOperator) {
                "+" -> A + B
                "-" -> A - B
                "*" -> A * B
                "/" -> A / B
                else -> "非法输入！"
            }

            println("运算结果为:$mResult")
            println("输入#结束，否则继续:")
            val dfhjksah = readln()
            if (dfhjksah == "#") break
        }
    }
}