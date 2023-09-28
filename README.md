# TasKT
## usage
```kt
class MyPlugin(): JavaPlugin() {

  var testVar: Boolean = false

  fun testFunc() {
    (0..10).forEach {
      println(it.toString())
    }

    //codes...
    testVar = true
  }
  override fun onEnable() {
    //코드들...
    val tasKT = TaskTimer(this)
    tasKT.asyncTaskTimer {
      run {
        testFunc()
      }
      delay(0L)
      period(20 * 3L)
      cancelTaskIf {
         tastVar == true
      }
    }.exec()
  }
}
```

```kt
delay(Long) : scheduler의 runTaskTimerAsynchronously 스케줄러가 진행될 때, 'delay' 항목에 해당하며, Long 타입을 인자로 받습니다.

period(Long) : scheduler의 runTaskTimerAsynchronously 스케줄러가 진행될 때, 'period' 항목에 해당하며, Long 타입을 인자로 받습니다.

run {  } : 이 블록 안의 코드를 실행합니다.

cancelTaskIf {  } : 안의 조건이 'true'라면 스케줄러를 cancel합니다.
```

추가 예정.

혹시 이 레포지토리가 맘에 안드시는 분들은 이슈 열어주세요!

개인 공부용으로 만들어봤습니다.

코드의 구조를 보고 "이딴게 개발자?"하는 말이 나올 수도 있는데, 아직 초보라 이해해주세요 :(
