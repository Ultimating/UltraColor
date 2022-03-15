package me.ultimategamer200.ultracolor.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.MinecraftVersion.V;

import java.io.PrintStream;
import java.util.logging.LogRecord;

/**
 * Represents the console filtering module
 */
public final class Filter {

	/**
	 * Start filtering the console - call this from the plugin in onStart (onEnable)
	 */
	public static void inject() {
		// Set filter for System out
		System.setOut(new FilterSystem());

		// Set filter for Bukkit
		final FilterLegacy filter = new FilterLegacy();

		for (final Plugin plugin : Bukkit.getPluginManager().getPlugins())
			plugin.getLogger().setFilter(filter);

		Bukkit.getLogger().setFilter(filter);

		// Set Log4j filter
		if (MinecraftVersion.atLeast(V.v1_7))
			FilterLog4j.inject();
	}

	/*
	 * Return true if the message is filtered
	 */
	static boolean isFiltered(String message) {
		if (message == null || message.isEmpty())
			return false;

		message = Common.stripColors(message);

		// Log4j2 exploit
		if (message.contains("${jndi:ldap:"))
			return true;

		// Filter inbuilt Foundation pagination scommand
		return message.contains("issued server command: /#flp");
	}
}

/**
 * The old Bukkit filter
 */
class FilterLegacy implements java.util.logging.Filter {

	@Override
	public boolean isLoggable(LogRecord record) {
		final String message = record.getMessage();

		return !Filter.isFiltered(message);
	}
}

/**
 * The System out filter
 */
class FilterSystem extends PrintStream {
	FilterSystem() {
		super(System.out);
	}

	@Override
	public void println(Object x) {
		if (x != null && !Filter.isFiltered(x.toString()))
			super.println(x);
	}

	@Override
	public void println(String x) {
		if (x != null && !Filter.isFiltered(x))
			super.println(x);
	}
}

/**
 * The new Log4j filter
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class FilterLog4j implements org.apache.logging.log4j.core.Filter {
	/*
	 * Starts logging for this filter
	 */
	static void inject() {
		try {
			((Logger) LogManager.getRootLogger()).addFilter(new FilterLog4j());

		} catch (final Throwable ex) {
			// Unsupported
		}
	}

	@Override
	public Result filter(LogEvent record) {
		return checkMessage(record.getMessage().getFormattedMessage());
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
		return checkMessage(message);
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
		return checkMessage(message.toString());
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
		return checkMessage(message.getFormattedMessage());
	}

	/*
	 * Return if the message should be filtered
	 */
	private Result checkMessage(String message) {
		return Filter.isFiltered(message) ? Result.DENY : Result.NEUTRAL;
	}

	/* ------------------------------------------------------------ */
	/* Implementation required methods */
	/* ------------------------------------------------------------ */

	@Override
	public Result getOnMatch() {
		return Result.NEUTRAL;
	}

	@Override
	public Result getOnMismatch() {
		return Result.NEUTRAL;
	}

	@Override
	public State getState() {
		try {
			return State.STARTED;
		} catch (final Throwable t) {
			return null;
		}
	}

	@Override
	public void initialize() {
	}

	@Override
	public boolean isStarted() {
		return true;
	}

	@Override
	public boolean isStopped() {
		return false;
	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6, Object arg7) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
		return null;
	}

	@Override
	public Result filter(Logger arg0, Level arg1, Marker arg2, String arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
		return null;
	}
}
