package com.extrawest.ocpp.emulator.chargepoint.cli.picocli;

import picocli.CommandLine;

public class FooterRenderer implements CommandLine.IHelpSectionRenderer {
    @Override
    public String render(CommandLine.Help help) {
        return "-----------------------Extrawest-----------------------" + System.lineSeparator();
    }
}
