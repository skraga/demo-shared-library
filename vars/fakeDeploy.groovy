def call(Map args) {
    assert args.address != null
    def count = args.count ?: '5'
    try {
        sh "ping -c ${count} ${args.address}"
    } catch (Exception e) {
        echo "Error while pinging: ${e}"
    }
}
