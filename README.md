# TreeView
首次提交(一个简单的树形控件)

创建步骤：
1.创建orgBean和fileBean，它们有四个属性，分别是id,pid,
label，desc
2.创建Node结点，
3.为orgBean和fileBean设置注解，
4.通过反射+注解的方式获取orgBean和fileBean的值，并将它们
保存到node集合中
5.为node集合中的所有结点设置父子类关系
6.为所有结点设置图片
7.得到按父子类顺序排序的结点集合
8.为树结点设置点击事件
