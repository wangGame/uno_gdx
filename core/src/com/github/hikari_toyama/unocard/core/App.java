//package com.github.hikari_toyama.unocard.core;
//
//import static com.github.hikari_toyama.unocard.core.Content.DRAW2;
//import static com.github.hikari_toyama.unocard.core.Content.SKIP;
//
//import java.util.List;
//
//public class App {
//    public static void main(String[] args) {
//        System.out.println(13 * (CardColorEnum.RED.ordinal() - 1) + Content.NUM0.ordinal());
//
//
//
//    }
//
//
//    private boolean mAIRunning;
//    private int mStatus;
//    private boolean mAuto;
//    private static final int STAT_IDLE = 0x1111;
//    private Uno mUno;
//    private AI mAI;
//    private CardColorEnum[] mBestCardColorEnum;
//    private void requestAI() {
//        int idxBest;
//
//        if (!mAIRunning) {
//            mAIRunning = true;
//            while (mStatus == Constant.COM1
//                    || mStatus == Constant.COM2
//                    || mStatus == Constant.COM3
//                    || (mStatus == Constant.YOU && mAuto)) {
//                setStatus(STAT_IDLE); // block tap down events when idle
//                idxBest = mAI.easyAI_bestCardIndex4NowPlayer(mBestCardColorEnum);
////                idxBest = mUno.getDifficulty() == Constant.LV_EASY
////                        ? mAI.easyAI_bestCardIndex4NowPlayer(mBestCardColorEnum)
////                        : mUno.isSevenZeroRule()
////                        ? mAI.sevenZeroAI_bestCardIndex4NowPlayer(mBestCardColorEnum)
////                        : mUno.is2vs2()
////                        ? mAI.teamAI_bestCardIndex4NowPlayer(mBestCardColorEnum)
////                        : mAI.hardAI_bestCardIndex4NowPlayer(mBestCardColorEnum);
//                if (idxBest >= 0) {
//                    // Found an appropriate card to play
//                    play(idxBest, mBestCardColorEnum[0]);
//                } // if (idxBest >= 0)
//                else {
//                    // No appropriate cards to play, or no card to play
//                    draw(1, /* force */ false);
//                } // else
//            } // while (mStatus == Player.COM1 || ...)
//
//            mAIRunning = false;
//        } // if (!mAIRunning)
//    } // requestAI()
//    private int mSelectedIdx;
//    private int mScore, mDiff;
//    private int mWinner;
//    private static final int STAT_SEVEN_TARGET = 0x7777;
//    private static final int STAT_DOUBT_WILD4 = 0x6666;
//    private static final int STAT_WILD_COLOR = 0x5555;
//    private static final int STAT_GAME_OVER = 0x4444;
//    private static final int STAT_NEW_GAME = 0x3333;
//    private static final int STAT_WELCOME = 0x2222;
//    private void play(int index, CardColorEnum cardColorEnum) {
//        Card card;
//        int c, now, size, width, next;
//
//        setStatus(STAT_IDLE); // block tap down events when idle
//        now = mUno.getNow();
//        size = mUno.getCurrPlayer().getHandSize();
//        card = mUno.play(now, index, cardColorEnum);
//        mSelectedIdx = -1;
////        mSoundPool.play(sndPlay, mSndVol, mSndVol, 1, 0, 1.0f);
////        if (card != null) {
////            mLayer[0].elem = card.image;
////            switch (now) {
////                case Player.COM1:
////                    width = 44 * Math.min(size, 13) + 136;
////                    mLayer[0].startLeft = 160 + index / 13 * 44;
////                    mLayer[0].startTop = 450 - width / 2 + index % 13 * 44;
////                    break; // case Player.COM1
//
////                case Player.COM2:
////                    width = 44 * size + 76;
////                    mLayer[0].startLeft = 800 - width / 2 + 44 * index;
////                    mLayer[0].startTop = 50;
////                    break; // case Player.COM2
////
////                case Player.COM3:
////                    width = 44 * Math.min(size, 13) + 136;
////                    mLayer[0].startLeft = 1320 - index / 13 * 44;
////                    mLayer[0].startTop = 450 - width / 2 + index % 13 * 44;
////                    break; // case Player.COM3
////
////                default:
////                    width = 44 * size + 76;
////                    mLayer[0].startLeft = 800 - width / 2 + 44 * index;
////                    mLayer[0].startTop = 680;
////                    break; // default
////            } // switch (now)
//
////            mLayer[0].endLeft = 1118;
////            mLayer[0].endTop = 360;
////            animate(1, mLayer);
//            if (size == 1) {
//                // The player in action becomes winner when it played the
//                // final card in its hand successfully
//                if (mUno.is2vs2()) {
//                    if (now == Constant.YOU || now == Constant.COM2) {
//                        mDiff = 2 * (mUno.getPlayer(Constant.COM1).getHandScore()
//                                + mUno.getPlayer(Constant.COM3).getHandScore());
//                        mScore = Math.min(9999, 200 + mScore + mDiff);
////                        mSoundPool.play(sndWin, mSndVol, mSndVol, 1, 0, 1.0f);
//                    } // if (now == Player.YOU || now == Player.COM2)
//                    else {
//                        mDiff = -2 * (mUno.getPlayer(Constant.YOU).getHandScore()
//                                + mUno.getPlayer(Constant.COM2).getHandScore());
//                        mScore = Math.max(-999, 200 + mScore + mDiff);
////                        mSoundPool.play(sndLose, mSndVol, mSndVol, 1, 0, 1.0f);
//                    } // else
//                } // if (mUno.is2vs2())
//                else if (now == Constant.YOU) {
//                    mDiff = mUno.getPlayer(Constant.COM1).getHandScore() +
//                            mUno.getPlayer(Constant.COM2).getHandScore() +
//                            mUno.getPlayer(Constant.COM3).getHandScore();
//                    mScore = Math.min(9999, 200 + mScore + mDiff);
////                    mSoundPool.play(sndWin, mSndVol, mSndVol, 1, 0, 1.0f);
//                } // else if (now == Player.YOU)
//                else {
//                    mDiff = -mUno.getPlayer(Constant.YOU).getHandScore();
//                    mScore = Math.max(-999, 200 + mScore + mDiff);
////                    mSoundPool.play(sndLose, mSndVol, mSndVol, 1, 0, 1.0f);
//                } // else
//
//                mAuto = false; // Force disable the AUTO switch
//                mWinner = now;
//                setStatus(STAT_GAME_OVER);
//            } // if (size == 1)
//            else {
//                // When the played card is an action card or a wild card,
//                // do the necessary things according to the game rule
//                if (size == 2) {
////                    mSoundPool.play(sndUno, mSndVol, mSndVol, 1, 0, 1.0f);
//                } // if (size == 2)
//
//                switch (card.getContent()) {
//                    case DRAW2:
//                        next = mUno.switchNow();
//                        if (mUno.isDraw2StackRule()) {
//                            c = mUno.getDraw2StackCount();
////                            refreshScreen(i18n.act_playDraw2(now, next, c));
////                            threadWait(1500);
//                            setStatus(next);
//                        } // if (mUno.isDraw2StackRule())
//                        else {
////                            refreshScreen(i18n.act_playDraw2(now, next, 2));
////                            threadWait(1500);
////                            draw(2, /* force */ true);
//                        } // else
//                        break; // case DRAW2
//
//                    case SKIP:
//                        next = mUno.switchNow();
////                        refreshScreen(i18n.act_playSkip(now, next));
////                        threadWait(1500);
//                        setStatus(mUno.switchNow());
//                        break; // case SKIP
//
//                    case REV:
//                        mUno.switchDirection();
////                        refreshScreen(i18n.act_playRev(now));
////                        threadWait(1500);
//                        setStatus(mUno.switchNow());
//                        break; // case REV
//
//                    case WILD:
////                        refreshScreen(i18n.act_playWild(now, cardColorEnum.ordinal()));
////                        threadWait(1500);
//                        setStatus(mUno.switchNow());
//                        break; // case WILD
//
//                    case WILD_DRAW4:
//                        next = mUno.getNext();
////                        refreshScreen(i18n.act_playWildDraw4(now, next));
////                        threadWait(1500);
//                        setStatus(STAT_DOUBT_WILD4);
//                        break; // case WILD_DRAW4
//
//                    case NUM7:
//                        if (mUno.isSevenZeroRule()) {
////                            refreshScreen(i18n.act_playCard(now, card.name));
////                            threadWait(750);
//                            setStatus(STAT_SEVEN_TARGET);
//                            break; // case NUM7
//                        } // if (mUno.isSevenZeroRule())
//                        // else fall through
//
//                    case NUM0:
//                        if (mUno.isSevenZeroRule()) {
////                            refreshScreen(i18n.act_playCard(now, card.name));
////                            threadWait(750);
//                            mUno.cycle();
//                            break; // case NUM0
//                        } // if (mUno.isSevenZeroRule())
//                        // else fall through
//
//                    default:
////                        refreshScreen(i18n.act_playCard(now, card.name));
////                        threadWait(1500);
//                        setStatus(mUno.switchNow());
//                        break; // default
//                } // switch (card.content)
//            } // else
//        } // if (card != null)
////    } // play(int, Color)
//    private void setStatus(int status) {
//        switch (mStatus = status) {
//            case STAT_WELCOME:
////                if (mAdjustOptions) {
////                    refreshScreen(i18n.info_ruleSettings());
////                } // if (mAdjustOptions)
////                else {
////                    refreshScreen(i18n.info_welcome());
////                } // else
//                break; // case STAT_WELCOME
//
//            case STAT_NEW_GAME:
//                // New game
//                mStatus = STAT_IDLE; // block tap down events when idle
//
//                // You will lose 200 points if you quit during the game
//                mScore -= 200;
//                mUno.start();
//                mSelectedIdx = -1;
////                refreshScreen(i18n.info_ready());
////                threadWait(2000);
//                switch (mUno.getRecentInfo()[3].card.content) {
//                    case DRAW2:
//                        // If starting with a [+2], let dealer draw 2 cards.
//                        draw(2, /* force */ true);
//                        break; // case DRAW2
//
//                    case SKIP:
//                        // If starting with a [skip], skip dealer's turn.
//                        refreshScreen(i18n.info_skipped(mUno.getNow()));
//                        threadWait(1500);
//                        setStatus(mUno.switchNow());
//                        break; // case SKIP
//
//                    case REV:
//                        // If starting with a [reverse], change the action
//                        // sequence to COUNTER CLOCKWISE.
//                        mUno.switchDirection();
//                        refreshScreen(i18n.info_dirChanged());
//                        threadWait(1500);
//                        setStatus(mUno.getNow());
//                        break; // case REV
//
//                    default:
//                        // Otherwise, go to dealer's turn.
//                        setStatus(mUno.getNow());
//                        break; // default
//                } // switch (mUno.getRecentInfo()[3].card.content)
//                break; // case STAT_NEW_GAME
//
//            case Player.YOU:
//                // Your turn, select a hand card to play, or draw a card
//                if (mAuto) {
//                    requestAI();
//                } // if (mAuto)
//                else if (mAdjustOptions) {
//                    refreshScreen("");
//                } // else if (mAdjustOptions)
//                else if (mUno.legalCardsCount4NowPlayer() == 0) {
//                    draw(1, /* force */ false);
//                } // else if (mUno.legalCardsCount4NowPlayer() == 0)
//                else if (mUno.getPlayer(Player.YOU).getHandSize() == 1) {
//                    play(0, CardColorEnum.NONE);
//                } // else if (mUno.getPlayer(Player.YOU).getHandSize() == 1)
//                else if (mSelectedIdx < 0) {
//                    int c = mUno.getDraw2StackCount();
//
//                    refreshScreen(c == 0
//                            ? i18n.info_yourTurn()
//                            : i18n.info_yourTurn_stackDraw2(c));
//                } // else if (mSelectedIdx < 0)
//                else {
//                    List<Card> hand = mUno.getPlayer(Player.YOU).getHandCards();
//                    Card card = hand.get(mSelectedIdx);
//
//                    refreshScreen(mUno.isLegalToPlay(card)
//                            ? i18n.info_clickAgainToPlay(card.name)
//                            : i18n.info_cannotPlay(card.name));
//                } // else
//                break; // case Player.YOU
//
//            case STAT_WILD_COLOR:
//                // Need to specify the following legal color after played a
//                // wild card. Draw color sectors in the center of screen
//                refreshScreen(i18n.ask_color());
//                break; // case STAT_WILD_COLOR
//
//            case STAT_DOUBT_WILD4:
//                if (mUno.getNext() == Player.YOU && !mAuto) {
//                    // Challenge or not is decided by you
//                    refreshScreen(i18n.ask_challenge(
//                            mUno.next2lastColor().ordinal()));
//                } // if (mUno.getNext() == Player.YOU && !mAuto)
//                else if (mAI.needToChallenge()) {
//                    // Challenge or not is decided by AI
//                    onChallenge();
//                } // else if (mAI.needToChallenge())
//                else {
//                    mUno.switchNow();
//                    draw(4, /* force */ true);
//                } // else
//                break; // case STAT_DOUBT_WILD4
//
//            case STAT_SEVEN_TARGET:
//                // In 7-0 rule, when someone put down a seven card, the player
//                // must swap hand cards with another player immediately.
//                if (mAuto || mUno.getNow() != Player.YOU) {
//                    // Seven-card is played by AI. Select target automatically.
//                    swapWith(mAI.calcBestSwapTarget4NowPlayer());
//                } // if (mAuto || mUno.getNow() != Player.YOU)
//                else {
//                    // Seven-card is played by you. Select target manually.
//                    refreshScreen(i18n.ask_target());
//                } // else
//                break; // case STAT_SEVEN_TARGET
//
//            case Player.COM1:
//            case Player.COM2:
//            case Player.COM3:
//                // AI players' turn
//                requestAI();
//                break; // case Player.COM1, Player.COM2, Player.COM3
//
//            case STAT_GAME_OVER:
//                // Game over
//                if (mAdjustOptions) {
//                    refreshScreen(i18n.info_ruleSettings());
//                } // if (mAdjustOptions)
//                else {
//                    refreshScreen(i18n.info_gameOver(mScore, mDiff));
//                } // else
//                break; // case STAT_GAME_OVER
//
//            default:
//                break; // default
//        } // switch (mStatus = status)
//    } // setStatus(int)
//
//}
