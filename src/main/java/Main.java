import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    private String message;
    private MessageChannel channel;
    private User user;

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(System.getenv("YichengDiscordToken"));
        builder.addEventListener(new Main());
        builder.buildAsync();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        message = event.getMessage().getContentRaw();
        channel = event.getChannel();
        user = event.getAuthor();

        if (user.isBot())
            return;
        else if (message.equals("-help"))
            help(event);
        else if (message.equals("-ping"))
            ping(event);
        else if (message.substring(0, 5).equals("-echo"))
            echo(event);
        else if (message.substring(0, 5).equals("-kick"))
            kick(event);
        else if (message.substring(0, 4).equals("-ban"))
            ban(event);
    }

    private void help(MessageReceivedEvent event) {
        event.getChannel().sendMessage("```\n" +
                "Command List\n" +
                "-help - Display this list\n" +
                "-ping - Ping the bot\n" +
                "-echo - Echo the user's input\n" +
                "\t(Optional parameter -h deletes original command)\n" +
                "-kick - Kick a user\n" +
                "-ban - Ban a user\n" +
                "```").queue();
    }

    private void ping(MessageReceivedEvent event) {
        event.getChannel().sendMessage("pong").queue();
    }

    private void echo(MessageReceivedEvent event) {
        if (message.substring(6, 8).equals("-h")) {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage(message.substring(9)).queue();
        } else
            event.getChannel().sendMessage(message.substring(6)).queue();
    }

    private void kick(MessageReceivedEvent event) {
        if(!event.getMessage().getMember().hasPermission((Permission.KICK_MEMBERS))) {
            event.getChannel().sendMessage("Nice try, " + event.getAuthor().getAsMention()).queue();
        } else {
            event.getGuild().getController().kick(event.getMessage().getMentionedMembers().get(0)).queue();
            event.getChannel().sendMessage("K").queue();
        }
    }

    private void ban(MessageReceivedEvent event) {
        if(!event.getMessage().getMember().hasPermission((Permission.BAN_MEMBERS))) {
            event.getChannel().sendMessage("Nice try, " + event.getAuthor().getAsMention()).queue();
        } else {
            event.getGuild().getController().ban(event.getMessage().getMentionedMembers().get(0), 0).queue();
            event.getChannel().sendMessage("K").queue();
        }
    }

}
