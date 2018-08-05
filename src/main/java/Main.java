import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(System.getenv("FrogDiscordToken"));
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (event.getAuthor().isBot())
            return;
        else if (message.equals("$ping"))
            event.getChannel().sendMessage("pong").queue();
        else if (message.equals("$help"))
            help(event);
        else if (message.substring(0, 5).equals("$echo"))
            echo(event);
    }

    private void help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("```\n" +
                "Command List\n" +
                "$help - Display this list\n" +
                "$echo - Echo the user's input\n" +
                "\t(Optional parameter -h deletes original command)\n" +
                "```").queue();
    }

    private void echo(MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        if (message.substring(6, 8).equals("-h")) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(message.substring(9)).queue();
        } else
            event.getChannel().sendMessage(message.substring(6)).queue();
    }

}
