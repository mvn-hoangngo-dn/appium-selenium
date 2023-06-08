package pages.appPages

class DragPageIOS: DragPage() {


    override fun dragAndDrop(): DragPage {
        println("Handling for IOS")
        dragAction(dragC1,dropC1)
        dragAction(dragL1,dropL1)
        dragAction(dragC3,dropC3)
        return this
    }

}
