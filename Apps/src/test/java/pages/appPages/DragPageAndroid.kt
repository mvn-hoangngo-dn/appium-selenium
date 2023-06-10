package pages.appPages

class DragPageAndroid: DragPage() {

    override fun dragAndDrop(): DragPage {
        println("Handling for Android")
        dragAction(dragC1,dropC1)
        dragAction(dragL1,dropL1)
        return this
    }

}
